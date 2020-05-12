----------------------------------------------------------------------
-- File: MicroSuma.vhd
-- Description: Simplified Micro MIPS, only addition with immediate data
-- Date last modification: 2019-02-22
-- Authors: 
-- Sbuject: Sbuject: C.E. 1st grade
-- Laboratory group:
-- Theory group:
-- Task: 2
-- Exercise: 3
----------------------------------------------------------------------



library IEEE;
use IEEE.std_logic_1164.ALL;
use IEEE.std_LOGIC_arith.ALL;
use IEEE.std_logic_unsigned.ALL;

entity MicroSuma is
	port (
		Clk : in std_logic; -- Clock
		NRst : in std_logic; -- Reset active low level
		MemProgAddr : out std_logic_vector(31 downto 0); -- Address for the program memory
		MemProgData : in std_logic_vector(31 downto 0) -- Operation code
	);
	end MicroSuma;

architecture Practica of MicroSuma is

	-- Declaration of RegsMIPS
	
	-- Declaration of ALUMIPS
	
	-- Declaration of auxiliary signal

begin

	-- Instantiation of RegMIPS
	
	-- Instantiation of ALUMIPS
	
	-- Sign Extension
	
	-- PC route


end Practica;

