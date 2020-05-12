library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;
use ieee.std_logic_arith.all;


entity MicroMIPS is

	port (
		Clk : in std_logic; -- Reloj
		NRst : in std_logic; -- Low level active Reset 
		MemProgAddr : out std_logic_vector(31 downto 0); -- Address for the program memory
		MemProgData : in std_logic_vector(31 downto 0); -- Operation code
		MemDataAddr : out std_logic_vector(31 downto 0); -- Address for the data memory
		MemDataDataRead : in std_logic_vector(31 downto 0); -- Data to read in the data memory
		MemDataDataWrite : out std_logic_vector(31 downto 0); -- Data to save in the data memory
		MemDataWE : out std_logic
	);

end entity;


architecture MicroMIPSarch of MicroMIPS is

	component alumips
	Port (
		Op1 : in std_logic_vector (31 downto 0);
		Op2 : in std_logic_vector (31 downto 0);
		ALUControl : in std_logic_vector (2 downto 0);
		Res : out std_logic_vector (31 downto 0);
		Z : out std_logic
		);
	end component;

	component unidadcontrol
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

	component regsmips
	port (
		Clk : in std_logic; -- Clock
		NRst : in std_logic; -- Asynchronous Reset in low level
		A1 : in std_logic_vector(4 downto 0); -- Address for the port Rd1
		Rd1 : out std_logic_vector(31 downto 0); -- Data of the port Rd1
		A2 : in std_logic_vector(4 downto 0); -- Address for the port Rd2
		Rd2 : out std_logic_vector(31 downto 0); -- Dataof the port Rd2
		A3 : in std_logic_vector(4 downto 0); -- Address for the port Wd3
		Wd3 : in std_logic_vector(31 downto 0); -- Input data Wd3
		We3 : in std_logic -- Enable of the register bank
	); 
	end component;

	component extcero
	port(
		entradacortacero : in std_logic_vector(15 downto 0);
		salidalargacero : out std_logic_vector(31 downto 0)
	);
	end component;

	component extsgn
	port(
		entradacortasgn : in std_logic_vector(15 downto 0);
		salidalargasgn : out std_logic_vector(31 downto 0)
	);
	end component;

	component pcounter
	port(
		PCviejo : in std_logic_vector(31 downto 0);
		PCretrasado : out std_logic_vector(31 downto 0);
		we : in std_logic;
		clk :in std_logic;
		nrst : in std_logic
	);
	end component;
-- SIGNALS  -------------------------------------------------------------------------------------------------------------
signal salu0, salusrcs, salusrc0, salus, sextcero0, sextcero1, sextceros, smemtoregs, sjumps, spc, spcsrcs, sjump1, spc4, sextcero0x4 : std_logic_vector(31 downto 0);

signal sJump, sBranch, sMemToReg, sMemWrite, sALUSrc, sextCero, sRegWrite, sRegDest, sz :std_logic;
signal sALUControl : std_logic_vector(2 downto 0);

signal sregdests : std_logic_vector(4 downto 0);


begin

	uutALUMIPS: alumips
	Port map(
		Op1 => salu0,
		Op2 => salusrcs,
		ALUControl => salucontrol,
		Res => salus,
		Z => sz
	);
	
	uutunidadcontrol: unidadcontrol
	port map(
		OPCode => memprogdata(31 downto 26),
		Funct => memprogdata(5 downto 0),
		Jump => sjump,
		Branch => sbranch,
		MemToReg => smemtoreg,
		MemWrite => smemwrite,
		ALUSrc => salusrc,
		ALUControl => salucontrol,
		ExtCero => sextcero,
		RegWrite => sregwrite,
		RegDest => sregdest
        );

	uutregsmips: regsmips
	port map(
		Clk => clk,
		NRst => nrst,
		A1 => memprogdata(25 downto 21),
		Rd1 => salu0, 
		A2 => memprogdata(20 downto 16),
		Rd2 => salusrc0,
		A3 => sregdests,
		Wd3 => smemtoregs,
		We3 => sregwrite
	); 

	uutextcero: extcero
	port map(
		entradacortacero => memprogdata(15 downto 0),
		salidalargacero => sextcero1
	);

	uutextsgn:extsgn
	port map(
		entradacortasgn => memprogdata(15 downto 0),
		salidalargasgn =>  sextcero0
	);

	uutpcounter: pcounter
	port map(
		PCviejo => sjumps,
		PCretrasado => spc,
		we => '1',
		clk => clk,
		nrst => nrst
	);

	salusrcs <= salusrc0 when salusrc = '0' else sextceros;
	sextceros <= sextcero0 when sextcero = '0' else sextcero1;
	sregdests <= memprogdata(20 downto 16) when sregdest = '0' else memprogdata(15 downto 11);
	smemtoregs <= salus when smemtoreg = '0' else memdatadataread;
	sjumps <= spcsrcs when sjump = '0' else sjump1;
	spc4 <= spc + 4;
	sjump1(31 downto 28) <= spc4(31 downto 28);
	sjump1(27 downto 2) <= memprogdata(25 downto 0);
	sjump1(1 downto 0) <= "00";
	sextcero0x4(31 downto 2) <= sextcero0(29 downto 0);
	sextcero0x4(1 downto 0) <= "00";
	spcsrcs <= sextcero0x4 + spc4 when (sbranch = '1' and sz = '1') else spc4;

	memdatawe <= smemwrite;
	MemDataAddr <= salus;
	MemDataDataWrite <= salusrc0;
	MemProgAddr <= spc;

end architecture;