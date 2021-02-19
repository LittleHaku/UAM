--------------------------------------------------------------------------------
-- Procesador MIPS con pipeline curso Arquitectura 2020-2021
--
-- Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
-- Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es
--
--------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;

entity processor is
   port(
      Clk         : in  std_logic; -- Reloj activo en flanco subida.
      Reset       : in  std_logic; -- Reset asincrono activo nivel alto.
      -- Instruction memory.
      IAddr      : out std_logic_vector(31 downto 0); -- Direccion Instr.
      IDataIn    : in  std_logic_vector(31 downto 0); -- Instruccion leida.
      -- Data memory.
      DAddr      : out std_logic_vector(31 downto 0); -- Direccion.
      DRdEn      : out std_logic;                     -- Habilitacion lectura.
      DWrEn      : out std_logic;                     -- Habilitacion escritura.
      DDataOut   : out std_logic_vector(31 downto 0); -- Dato escrito.
      DDataIn    : in  std_logic_vector(31 downto 0)  -- Dato leido.
   );
end processor;

architecture rtl of processor is

  component alu
    port(
      OpA : in std_logic_vector (31 downto 0);
      OpB : in std_logic_vector (31 downto 0);
      Control : in std_logic_vector (3 downto 0);
      Result : out std_logic_vector (31 downto 0);
      Zflag : out std_logic
    );
  end component;

  component reg_bank
     port (
        Clk   : in std_logic; -- Reloj activo en flanco de subida.
        Reset : in std_logic; -- Reset asicrono a nivel alto.
        A1    : in std_logic_vector(4 downto 0);   -- Direccion para el puerto Rd1.
        Rd1   : out std_logic_vector(31 downto 0); -- Dato del puerto Rd1.
        A2    : in std_logic_vector(4 downto 0);   -- Direccion para el puerto Rd2.
        Rd2   : out std_logic_vector(31 downto 0); -- Dato del puerto Rd2.
        A3    : in std_logic_vector(4 downto 0);   -- Direccion para el puerto Wd3.
        Wd3   : in std_logic_vector(31 downto 0);  -- Dato de entrada Wd3.
        We3   : in std_logic -- Habilitacion de la escritura de Wd3.
     );
  end component reg_bank;

  component control_unit
     port (
        -- Entrada = codigo de operacion en la instruccion:
        OpCode   : in  std_logic_vector (5 downto 0);
        -- Seniales para el PC
        Branch   : out  std_logic; -- 1 = Ejecutandose instruccion branch.
        -- Seniales relativas a la memoria.
        MemToReg : out  std_logic; -- 1 = Escribir en registro la salida de la mem.
        MemWrite : out  std_logic; -- Escribir la memoria.
        MemRead  : out  std_logic; -- Leer la memoria.
        -- Seniales para la ALU
        ALUSrc   : out  std_logic;                     -- 0 = oper.B es registro, 1 = es valor inm.
        ALUOp    : out  std_logic_vector (2 downto 0); -- Tipo operacion para control de la ALU.
        Jump     : out std_logic; -- 1 Para el salto de PC.
        -- Seniales para el GPR
        RegWrite : out  std_logic; -- 1=Escribir registro.
        RegDst   : out  std_logic  -- 0=Reg. destino es rt, 1=rd.
     );
  end component;

  component alu_control is
   port (
      -- Entradas:
      ALUOp  : in std_logic_vector (2 downto 0); -- Codigo de control desde la unidad de control.
      Funct  : in std_logic_vector (5 downto 0); -- Campo "funct" de la instruccion.
      -- Salida de control para la ALU:
      ALUControl : out std_logic_vector (3 downto 0) -- Define operacion a ejecutar por la ALU.
   );
 end component alu_control;

  -- Dato a escribir (registro o memoria).
  signal WB_reg_RD_data     : std_logic_vector(31 downto 0);

  -- Seniales en relacion al registro destino (en el caso de las instrucciones R).
  signal ID_reg_RD       : std_logic_vector(4 downto 0);
  signal EX_reg_RD       : std_logic_vector(4 downto 0);

  -- Seniales en relacion al dato guardado en el registro source.
  signal ID_reg_RS       : std_logic_vector(31 downto 0);
  signal EX_reg_RS       : std_logic_vector(31 downto 0);

  -- Seniales en relacion al dato guardado en el registro temporal.
  signal ID_reg_RT       : std_logic_vector(31 downto 0);
  signal EX_reg_RT       : std_logic_vector(31 downto 0);
  signal MEM_reg_RT      : std_logic_vector(31 downto 0);
   
  -- Seniales en relacion al registro destino.
  signal EX_reg_Dest     : std_logic_vector(4 downto 0);

  -- Seniales en relacion al comparador (rs==rt).
  signal EX_Regs_eq_branch  : std_logic;  --es igual al uno si son iguales.
  signal MEM_Regs_eq_branch : std_logic;

  -- Seniales en relacion al PC_counter.
  signal PC_next        : std_logic_vector(31 downto 0);
  signal PC_reg         : std_logic_vector(31 downto 0);
  signal IF_PC_plus4    : std_logic_vector(31 downto 0);
  signal ID_PC_plus4    : std_logic_vector(31 downto 0);
  signal EX_PC_plus4    : std_logic_vector(31 downto 0);

  -- Seniales en relacion con la instruccion.
  signal IF_Instruction : std_logic_vector(31 downto 0); -- La instruccion desde la mem de instr.
  signal ID_Instruction : std_logic_vector(31 downto 0);

  -- Seniales en relacion al dato inmediato.
  signal ID_Inm_ext     : std_logic_vector(31 downto 0); -- La parte baja de la instruccion extendida de signo.
  signal EX_Inm_ext     : std_logic_vector(31 downto 0);
  
  
  signal MEM_dataIn_Mem : std_logic_vector(31 downto 0); -- From Data Memory.
  signal WB_dataIn_Mem  : std_logic_vector(31 downto 0);
  

  -- Seniales de control.
  -- En la etapa ID.
  signal ID_Ctrl_Jump         : std_logic;
  signal ID_Ctrl_Branch       : std_logic; 
  signal ID_Ctrl_MemWrite     : std_logic; 
  signal ID_Ctrl_MemRead      : std_logic;  
  signal ID_Ctrl_ALUSrc       : std_logic; 
  signal ID_Ctrl_RegDest      : std_logic; 
  signal ID_Ctrl_MemToReg     : std_logic; 
  signal ID_Ctrl_RegWrite     : std_logic;
  signal ID_Ctrl_ALUOP        : std_logic_vector(2 downto 0);
  
  -- En la etapa EX.
  signal EX_Ctrl_Jump         : std_logic;
  signal EX_Ctrl_Branch       : std_logic; 
  signal EX_Ctrl_MemWrite     : std_logic; 
  signal EX_Ctrl_MemRead      : std_logic;  
  signal EX_Ctrl_ALUSrc       : std_logic; 
  signal EX_Ctrl_RegDest      : std_logic; 
  signal EX_Ctrl_MemToReg     : std_logic; 
  signal EX_Ctrl_RegWrite     : std_logic;
  signal EX_Ctrl_ALUOP        : std_logic_vector(2 downto 0);
  
  -- En la etapa MEM.
  signal MEM_Ctrl_Jump        : std_logic;
  signal MEM_Ctrl_Branch      : std_logic; 
  signal MEM_Ctrl_MemWrite    : std_logic;
  signal MEM_Ctrl_MemRead     : std_logic; 
  signal MEM_Ctrl_MemToReg    : std_logic; 
  signal MEM_Ctrl_RegWrite    : std_logic;

  -- En la etapa WB.
  signal WB_Ctrl_MemToReg     : std_logic;
  signal WB_Ctrl_RegWrite     : std_logic;

  -- Seniales en relacion con la ALU.
  signal EX_Alu_Res           : std_logic_vector(31 downto 0); -- resultado de la ALU.
  signal MEM_Alu_Res          : std_logic_vector(31 downto 0);
  signal WB_Alu_Res           : std_logic_vector(31 downto 0);
  signal EX_AluControl        : std_logic_vector(3 downto 0);  
  signal Alu_Op2              : std_logic_vector(31 downto 0);
  signal ALU_Igual            : std_logic;   -- comparador.

  -- Seniales en relacion con el salto jump.
  signal ID_Jump_address      : std_logic_vector(25 downto 0); -- parte de la instruccion para calcular la direccion de salto.
  signal EX_Jump_address      : std_logic_vector(25 downto 0);
  signal Addr_Jump            : std_logic_vector(31 downto 0); -- direccion de salto jump.
  
  -- Seniales en relacion con el salto branch.
  signal Addr_Branch          : std_logic_vector(31 downto 0); -- direccion de salto branch.

  -- Seniales en relacion a la direccion de salto.
  signal desition_Jump        : std_logic; -- decide si es salto jump/branch(=1), o no(=0), es decir, PC+4.
  signal EX_Addr_Jump_dest    : std_logic_vector(31 downto 0); --direccion de salto jump si desitio_Jump = 1, en otro caso, salto branch.
  signal MEM_Addr_Jump_dest   : std_logic_vector(31 downto 0);
  
  -- Seniales que controlan que dato entrara a la ALU.
  signal forwardA			  : std_logic_vector(1 downto 0);
  signal forwardB			  : std_logic_vector(1 downto 0);
  
  -- Es el numero de registro en su etapa correspondiente.
  signal ID_reg_RS_number            : std_logic_vector(4 downto 0);
  signal ID_reg_RT_number            : std_logic_vector(4 downto 0);
  signal EX_reg_RS_number            : std_logic_vector(4 downto 0);
  signal EX_reg_RT_number            : std_logic_vector(4 downto 0);
  signal MEM_reg_RD_number           : std_logic_vector(4 downto 0);
  signal WB_reg_RD_number            : std_logic_vector(4 downto 0);
  
  -- Es el dato que entraria en la ALU si no hubiese forwarding.
  signal EX_reg_RS_pre_fwd           : std_logic_vector(31 downto 0);
  signal EX_reg_RT_pre_fwd           : std_logic_vector(31 downto 0);
  
  -- Es el dato que se leeria de los registros si no hubiese forwarding registro a registro, caso 2 del ejercicio 1.
  signal ID_reg_RS_pre_reg2reg_fwd   : std_logic_vector(31 downto 0);
  signal ID_reg_RT_pre_reg2reg_fwd   : std_logic_vector(31 downto 0);
  
  --enables de los registros intermedios
  signal PC_enable                   : std_logic;
  signal IF_ID_enable                : std_logic; 

  -- hazard cuando hay un problema LW.
  signal hazard_control           : std_logic;

begin
  -- Mira si hay un salto, sino coge el PC+4
  PC_next <= MEM_Addr_Jump_dest when desition_Jump = '1' else IF_PC_plus4;

  PC_reg_proc: process(Clk, Reset)
  begin
    if Reset = '1' then
		PC_reg <= (others => '0');
    elsif rising_edge(Clk) then
      if PC_enable = '1' then
		PC_reg <= PC_next;
      end if;
    end if;
  end process;

  PC_enable <= '0' when hazard_control = '1' else
                '1';

  IF_PC_plus4    <= PC_reg+4;
  IAddr          <= PC_reg;
  IF_Instruction <= IDataIn;
  
  IF_ID_reg: process(Clk, Reset)
  begin
    if Reset = '1' then
      ID_PC_plus4    <= (others => '0');
      ID_Instruction <= (others => '0');
    elsif rising_edge(Clk) then 
      if IF_ID_enable = '1' then
        ID_PC_plus4    <= IF_PC_plus4;
        ID_Instruction <= IF_Instruction;
      end if;
    end if;
  end process;

  IF_ID_enable <= '0' when hazard_control = '1' else
                  '1';

  RegsMIPS : reg_bank
  port map (
    Clk   => Clk,
    Reset => Reset,
    A1    => ID_reg_RS_number,
    Rd1   => ID_reg_RS_pre_reg2reg_fwd,
    A2    => ID_reg_RT_number,
    Rd2   => ID_reg_RT_pre_reg2reg_fwd,
    A3    => WB_reg_RD_number,
    Wd3   => WB_reg_RD_data,
    We3   => WB_Ctrl_RegWrite
  );

  UnidadControl : control_unit
  port map(
    OpCode   => ID_Instruction(31 downto 26),
    -- Seniales para el PC.
    Branch   => ID_Ctrl_Branch,
    -- Seniales para la memoria.
    MemToReg => ID_Ctrl_MemToReg,
    MemWrite => ID_Ctrl_MemWrite,
    MemRead  => ID_Ctrl_MemRead,
    -- Seniales para la ALU.
    ALUSrc   => ID_Ctrl_ALUSrc,
    ALUOP    => ID_Ctrl_ALUOP,
    Jump     => ID_Ctrl_Jump,
    -- Seniales para el GPR.
    RegWrite => ID_Ctrl_RegWrite,
    RegDst   => ID_Ctrl_RegDest
  );

  -- Guardamos los registros de RT y RD para decidir el destino despues. Y el RS para el fordwarding (RT también necesario)
  ID_reg_RD       <= ID_Instruction(15 downto 11); 

  ID_reg_RS_number <= ID_Instruction(25 downto 21);
  ID_reg_RT_number <= ID_Instruction(20 downto 16);

  ID_Inm_ext      <= x"FFFF" & ID_Instruction(15 downto 0) when ID_Instruction(15)='1' else
                    x"0000" & ID_Instruction(15 downto 0);
  
  ID_Jump_address <= ID_Instruction(25 downto 0);
  
  --Hazard detention unit

  hazard_control <= '1' when EX_Ctrl_MemRead = '1' and (EX_reg_RT_number = ID_reg_RS_number or EX_reg_RT_number = ID_reg_RT_number) else
                    '0';


  -- Adelanto a la ALU.
  -- 01 adelanta de la etapa MEM, 10 adelanta de la etapa WB, 00 no adelanta.
  forwardA <= "01" when MEM_Ctrl_RegWrite = '1' and MEM_reg_RD_number = EX_reg_RS_number  and EX_reg_RS_number /= x"0000" else
			 "10" when WB_Ctrl_RegWrite = '1' and WB_reg_RD_number = EX_reg_RS_number  and EX_reg_RS_number /= x"0000" else
			 "00";
			 
  forwardB <= "01" when MEM_Ctrl_RegWrite = '1' and MEM_reg_RD_number = EX_reg_RT_number  and EX_reg_RT_number /= x"0000" else
			 "10" when WB_Ctrl_RegWrite = '1' and WB_reg_RD_number = EX_reg_RT_number  and EX_reg_RT_number /= x"0000" else
			 "00";
  
  EX_reg_RS <= MEM_Alu_Res when forwardA = "01" else
			  WB_reg_RD_data when forwardA = "10" else
			  EX_reg_RS_pre_fwd;
  
  EX_reg_RT <= MEM_Alu_Res when forwardB = "01" else
			  WB_reg_RD_data when forwardB = "10" else
			  EX_reg_RT_pre_fwd;
  
  -- Adelanto al registro ID_reg.
  ID_reg_RS <= WB_reg_RD_data when WB_Ctrl_RegWrite = '1' and WB_reg_RD_number = ID_reg_RS_number  and ID_reg_RS_number /= x"0000" else
			   ID_reg_RS_pre_reg2reg_fwd;
			   
  ID_reg_RT <= WB_reg_RD_data when WB_Ctrl_RegWrite = '1' and WB_reg_RD_number = ID_reg_RT_number  and ID_reg_RT_number /= x"0000" else
			   ID_reg_RT_pre_reg2reg_fwd;


  ID_EX_reg: process(Clk, Reset)
  begin
    if Reset = '1' then
      EX_PC_plus4         <= (others => '0');
      EX_reg_RS_pre_fwd   <= (others => '0');
      EX_reg_RT_pre_fwd   <= (others => '0'); 
      EX_reg_RD           <= (others => '0');
      EX_Inm_ext          <= (others => '0');
      EX_Jump_address     <= (others => '0');
      EX_Ctrl_Jump        <= '0';
      EX_Ctrl_Branch      <= '0';
      EX_Ctrl_MemWrite    <= '0';
      EX_Ctrl_MemRead     <= '0';
      EX_Ctrl_ALUSrc      <= '0';
      EX_Ctrl_RegDest     <= '0';
      EX_Ctrl_MemToReg    <= '0';
      EX_Ctrl_RegWrite    <= '0';
      EX_Ctrl_ALUOP       <= (others => '0');
	    EX_reg_RS_number    <= (others => '0');
	    EX_reg_RT_number    <= (others => '0');
    elsif rising_edge(Clk) then
      if hazard_control = '1' then
		EX_Ctrl_Jump        <= '0';
        EX_Ctrl_Branch      <= '0';
        EX_Ctrl_MemWrite    <= '0';
        EX_Ctrl_MemRead     <= '0';
        EX_Ctrl_RegWrite    <= '0';
      else 
        EX_PC_plus4         <= ID_PC_plus4;
        EX_reg_RS_pre_fwd   <= ID_reg_RS;
        EX_reg_RT_pre_fwd   <= ID_reg_RT;
        EX_reg_RD           <= ID_reg_RD;
        EX_Inm_ext          <= ID_Inm_ext; 
        EX_Jump_address     <= ID_Jump_address;
        EX_Ctrl_Jump        <= ID_Ctrl_Jump;
        EX_Ctrl_Branch      <= ID_Ctrl_Branch;
        EX_Ctrl_MemWrite    <= ID_Ctrl_MemWrite;
        EX_Ctrl_MemRead     <= ID_Ctrl_MemRead;
        EX_Ctrl_ALUSrc      <= ID_Ctrl_ALUSrc;
        EX_Ctrl_RegDest     <= ID_Ctrl_RegDest;
        EX_Ctrl_MemToReg    <= ID_Ctrl_MemToReg;
        EX_Ctrl_RegWrite    <= ID_Ctrl_RegWrite;
		EX_Ctrl_ALUOP       <= ID_Ctrl_ALUOP;
  	    EX_reg_RS_number    <= ID_reg_RS_number;
  	    EX_reg_RT_number    <= ID_reg_RT_number;
      end if;
    end if;
  end process;

  Alu_control_i: alu_control
  port map(
    -- Entradas:
    ALUOp  => EX_Ctrl_ALUOP, -- Codigo de control desde la unidad de control.
    Funct  => EX_Inm_ext (5 downto 0), -- Campo "funct" de la instruccion.
    -- Salida de control para la ALU:
    ALUControl => EX_AluControl -- Define operacion a ejecutar por la ALU
  );

  Alu_MIPS : alu
  port map (
    OpA     => EX_reg_RS,
    OpB     => Alu_Op2,
    Control => EX_AluControl,
    Result  => EX_Alu_Res,
    Zflag   => ALU_IGUAL
  );

  -- Calculamos las direcciones de jump y branch.
  Addr_Jump   <= EX_PC_plus4(31 downto 28) & EX_Jump_address & "00";

  Addr_Branch <= EX_PC_plus4 + ( EX_Inm_ext(29 downto 0) & "00");

  -- Multiplexor para saltar
  EX_Addr_Jump_dest <= Addr_Jump   when EX_Ctrl_Jump='1' else
                       Addr_Branch when EX_Ctrl_Branch='1' else
                      (others =>'0');
          
  -- Multiplexor para elegir el segundo operando de la ALU.
  Alu_Op2    <= EX_reg_RT when EX_Ctrl_ALUSrc = '0' else EX_Inm_ext;

  -- Multiplexor para elegir registro destino.
  EX_reg_Dest  <= EX_reg_RT_number when EX_Ctrl_RegDest = '0' else EX_reg_RD;

  EX_Regs_eq_branch <= '1' when (EX_reg_RS = EX_reg_RT) else '0';

  EX_MEM_reg: process(Clk, Reset)
  begin
    if Reset = '1' then
		MEM_Addr_Jump_dest  <= (others => '0');
		MEM_reg_RT          <= (others => '0');
		MEM_Alu_Res         <= (others => '0');
		MEM_Regs_eq_branch  <= '0';
		MEM_Ctrl_Jump       <= '0';
		MEM_Ctrl_Branch     <= '0';
		MEM_Ctrl_MemWrite   <= '0';
		MEM_Ctrl_MemRead    <= '0';   
		MEM_Ctrl_MemToReg   <= '0';
		MEM_Ctrl_RegWrite   <= '0';
		MEM_reg_RD_number   <= (others => '0');
    elsif rising_edge(Clk) then
		MEM_reg_RT          <= EX_reg_RT;
		MEM_Ctrl_Jump       <= EX_Ctrl_Jump;
		MEM_Ctrl_Branch     <= EX_Ctrl_Branch;
		MEM_Ctrl_MemWrite   <= EX_Ctrl_MemWrite;
		MEM_Ctrl_MemRead    <= EX_Ctrl_MemRead;
		MEM_Ctrl_MemToReg   <= EX_Ctrl_MemToReg;
		MEM_Ctrl_RegWrite   <= EX_Ctrl_RegWrite;
		MEM_Addr_Jump_dest  <= EX_Addr_Jump_dest;
		MEM_Alu_Res         <= EX_Alu_Res;
		MEM_Regs_eq_branch  <= EX_Regs_eq_branch;
		MEM_reg_RD_number   <= EX_reg_Dest;
    end if;
  end process;

  -- destination_Jump es 1 si hay un jump (j) o hay un branch y los registros implicados son iguales.
  desition_Jump  <= MEM_Ctrl_Jump or (MEM_Ctrl_Branch and MEM_Regs_eq_branch);

  DAddr      <= MEM_Alu_Res;
  DDataOut   <= MEM_reg_RT;
  DWrEn      <= MEM_Ctrl_MemWrite;
  dRdEn      <= MEM_Ctrl_MemRead;
  MEM_dataIn_Mem <= DDataIn;

  MEM_WB_reg: process(Clk, Reset)
  begin
    if Reset = '1' then
		WB_Alu_Res          <= (others => '0');
		WB_dataIn_Mem       <= (others => '0');
	    WB_Ctrl_MemToReg    <= '0';
		WB_Ctrl_RegWrite    <= '0';
	    WB_reg_RD_number    <= (others => '0');
    elsif rising_edge(Clk) then
		WB_Ctrl_MemToReg    <= MEM_Ctrl_MemToReg;
		WB_Ctrl_RegWrite    <= MEM_Ctrl_RegWrite;
		WB_Alu_Res          <= MEM_Alu_Res;
		WB_dataIn_Mem       <= MEM_dataIn_Mem;
	    WB_reg_RD_number    <= MEM_reg_RD_number;
    end if;
  end process;

  -- Multiplexor para ver donde va el dato, si a memoria o a registro.
  WB_reg_RD_data <= WB_dataIn_Mem when WB_Ctrl_MemToReg = '1' else WB_Alu_Res;

end architecture;