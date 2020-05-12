----------------------------------------------------------------------
-- File: UnidadControlTb.vhd
-- Description: Testbench for the control unit for the microprocessor MIPS
-- Date last modification: 2019-03-29

-- Authors: Sofía Martínez (2019),Alberto Sánchez (2012-2018), Fernando López Colino (2010), AdC (2014-2015)
-- Sbuject: Sbuject: C.E. 1st grade
-- Laboratory group:
-- Theory group:
-- Task: 4
-- Exercise: 1
----------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;
 
entity UnidadControlTb is
end UnidadControlTb;
 
architecture behavior of UnidadControlTb is
 
   type CasosPruebaT is record
		instruccion : std_logic_vector(31 downto 0);
		sigControl :  std_logic_vector(9 downto 0);
		aluControl : std_logic_vector(2 downto 0);
	end record;

	component UnidadControl
	port(
		OPCode : in  std_logic_vector (5 downto 0); -- OPCode of the instruction
		Funct : in std_logic_vector(5 downto 0); -- Funct of the instruction
		-- Signals for the PC
		Jump : out  std_logic;
--		RegToPC : out std_logic;
		Branch : out  std_logic;
--		PCToReg : out std_logic;
		-- Signals to the memory
		MemToReg : out  std_logic;
		MemWrite : out  std_logic;
		
		-- Sifnals for the ALU
		ALUSrc : out  std_logic;
		ALUControl : out  std_logic_vector (2 downto 0);
		ExtCero : out std_logic;
		
		-- Signals for the GPR
		RegWrite : out  std_logic;
		RegDest : out  std_logic
        );
	end component;

	
   --Inputs
   signal OPCode : std_logic_vector(5 downto 0) := (others => '0');
   signal funct : std_logic_vector(5 downto 0) := (others => '0');
   

 	--Outputs
   signal regWrite, ALUSrc, branch, memWrite, memToReg, jump, pcToReg, extCero, regToPC, RegDest : std_logic;
	signal ALUControl : std_logic_vector(2 downto 0);
   
	
	signal controlFlags : std_logic_vector(9 downto 0);
	
	signal instruc : std_logic_vector(31 downto 0);
	
	-- Numer of cases
	constant NUMCASOSPRUEBA : integer := 14;

	-- Matrix of cases
	type CasosPruebaT2 is array (1 to NUMCASOSPRUEBA) of CasosPruebaT;

  
  constant casos_prueba : CasosPruebaT2 := (

	-- 1 INSTRUCTION: lw
	--	INSTRUCTION		SENIALES_CTRL	ALU_OP
	(	x"8c012000",	"1100100000",	"010"),
	-- 2 INSTRUCTION: sw
	--	INSTRUCTION		SENIALES_CTRL	ALU_OP
	(	x"ac012004",	"0101-0-00-",	"010"),
	-- 3 INSTRUCTION: add
	--	INSTRUCTION		SENIALES_CTRL	ALU_OP
	(	x"00430820",	"1000000-01",	"010"),
	-- 4 INSTRUCTION: sub
	--	INSTRUCTION		SENIALES_CTRL	ALU_OP
	(	x"00430822",	"1000000-01",	"110"),
	-- 5 INSTRUCTION: and
	--	INSTRUCTION		SENIALES_CTRL	ALU_OP
	(	x"00000024",	"1000000-01",	"000"),
	-- 6 INSTRUCTION: or
	--	INSTRUCTION		SENIALES_CTRL	ALU_OP
	(	x"00221825",	"1000000-01",	"001"),
	-- 7 INSTRUCTION: slt
	--	INSTRUCTION		SENIALES_CTRL	ALU_OP
	(	x"0000002a",	"1000000-01",	"111"),
	-- 8 INSTRUCTION: nor
	--	INSTRUCTION		SENIALES_CTRL	ALU_OP
	(	x"00250827",	"1000000-01",	"101"),
	-- 9 INSTRUCTION: beq
	--	INSTRUCTION		SENIALES_CTRL	ALU_OP
	(	x"10000002",	"0010-0--0-",	"110"),
	-- 10 INSTRUCTION: j
	--	INSTRUCTION		SENIALES_CTRL	ALU_OP
	(	x"0800000b",	"0--0-1--0-",	"---"),
	-- INSTRUCTIONS ALU INMEDIATAS
	-- 11 INSTRUCTION: addi
	--	INSTRUCTION		SENIALES_CTRL	ALU_OP
	(	x"20410005",	"1100000000",	"010"),
	-- 12 INSTRUCTION: andi
	--	INSTRUCTION		SENIALES_CTRL	ALU_OP
	(	x"30410005",	"1100000100",	"000"),
	-- 13 INSTRUCTION: ori
	--	INSTRUCTION		SENIALES_CTRL	ALU_OP
	(	x"34230001",	"1100000100",	"001"),
	-- 14 INSTRUCTION: slti
	--	INSTRUCTION		SENIALES_CTRL	ALU_OP
	(	x"28410005",	"1100000000",	"111")
	);
	
	 -- Time we are going to wait to the response of the UC  
  constant tdelay : time := 10 ns;
  
  signal fallos : integer := 0;
  
BEGIN
 

   uut: UnidadControl PORT MAP (
      OPCode => OPCode,
		Funct => funct,
		jump => jump,
		Branch => branch,
		MemToReg => memToReg,
		MemWrite => memWrite,
		ALUSrc => ALUSrc,
		ALUControl => ALUControl,
		ExtCero => extCero,
		RegWrite => regWrite,
		RegDest => RegDest
	);
 
   --controlFlags <= regWrite & ALUSrc & branch & memWrite & memToReg & jump & pcToReg & extCero & regToPC & RegDest;
	controlFlags <= regWrite & ALUSrc & branch & memWrite & memToReg & jump & '-' & extCero & '-' & RegDest;

   -- Stimulus process
	stim_proc: process
   begin		
	     
		for i in 1 to NUMCASOSPRUEBA loop
		
			OPCode <= casos_prueba(i).INSTRUCCION(31 downto 26);
			funct <= casos_prueba(i).INSTRUCCION(5 downto 0);
			
			instruc <= casos_prueba(i).INSTRUCCION;
			
			wait for tdelay;
			
			assert std_match(controlFlags,casos_prueba(i).sigControl)
			report "error in signals in case " & integer'image(i) & " of instructions"
			severity warning;
			
			if not std_match(controlFlags,casos_prueba(i).sigControl) then
				fallos <= fallos + 1;
			end if;
			
			assert std_match(aluControl,casos_prueba(i).aluControl)
			report "error in ALUControl case " & integer'image(i) & " of instructions"
			severity warning;
			
			if not std_match(ALUControl,casos_prueba(i).aluControl) then
				fallos <= fallos + 1;
			end if;
			
			
		end loop;
		
		wait for tdelay;
		
		report "Finished Simulation";
		report "Number of errors: " & integer'image(fallos) severity note;
				
		wait;
	end process;

END;
