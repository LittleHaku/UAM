----------------------------------------------------------------------
-- File: ALUMIPS.vhd
-- Description: ALU of the microprocessor MIPS
-- Date last modification: 

-- Authors: 
-- Sbuject: Sbuject: C.E. 1st grade
-- Laboratory group:
-- Theory group:
-- Task: 2
-- Exercise: 2
----------------------------------------------------------------------


library IEEE;
use IEEE.std_logic_1164.ALL;
use IEEE.std_LOGIC_arith.ALL;
use IEEE.std_logic_signed.ALL;

entity ALUMIPS is
	Port (
		Op1 : in std_logic_vector (31 downto 0);
		Op2 : in std_logic_vector (31 downto 0);
		ALUControl : in std_logic_vector (2 downto 0);
		Res : out std_logic_vector (31 downto 0);
		Z : out std_logic
		);
end ALUMIPS;

architecture Practica of ALUMIPS is

signal sres: std_logic_vector(31 downto 0);

begin

	sres <= Op1 + Op2 when ALUcontrol = "010" else
		Op1 - Op2 when ALUcontrol = "110" else
		OP1 and OP2 when ALUControl = "000" else
		OP1 or OP2 when ALUControl = "001" else
		(others => '0') when ALUCONTROL = "111" and Op1 > OP2 else
		(others => '0') when ALUCONTROL = "111" and op1 = op2 else
		(0 => '1', others => '0') when alucontrol = "111" and op1 < op2 else
		op1 nor op2;

 	res <= sres;

	z <= '1' when conv_integer(sres) = 0 else '0';

end Practica;
