----------------------------------------------------------------------
-- File: MicroSumaTb.vhd
-- Description: Bank of tests for the simplified Micro MIPS
-- Date last modification: 2019-02-22
-- Authors: Sofía Martínez (2019), Alberto Sánchez (2012), Ángel de Castro (2011, 2015) 
-- Sbuject: Sbuject: C.E. 1st grade
-- Laboratory group:
-- Theory group:
-- Task: 2
-- Exercise: 3
----------------------------------------------------------------------



library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.std_logic_unsigned.all;
use IEEE.std_logic_arith.all;

entity MicroSumaTb is
end MicroSumaTb;
 
architecture Test OF MicroSumaTb is
 
  -- Declaration of micro(without memory)
	component MicroSuma
	port (
		Clk : in std_logic; -- Clock
		NRst : in std_logic; -- Reset active in low level
		MemProgAddr : out std_logic_vector(31 downto 0); -- Address for the program memory
		MemProgData : in std_logic_vector(31 downto 0) -- Operation code
	);
	end component;

	-- Declaration of the code memory/program memory
	component MemProgSuma
	port (
		MemProgAddr : in std_logic_vector(31 downto 0); -- Address for the program memory
		MemProgData : out std_logic_vector(31 downto 0) -- Operation code
	);
	end component;

	-- Inputs of micro
	-- In the test bank it could be used initial values in the statements,
	-- but no in the modules due to they are not synthesizable.
	signal memProgData : std_logic_vector(31 downto 0);
	signal nRst : std_logic := '0';
	signal clk : std_logic := '0';

	-- Outputs of micro
	signal memProgAddr : std_logic_vector(31 downto 0);

	-- Clock period
	constant CLKPERIOD : time := 10 ns;

	-- End of simulation, in case we want to stop the simulation if there is no events
	signal finSimu : boolean := false;

begin
 
	-- Instantiation of micro
	uut: MicroSuma
	port map(
		CLK => clk,
		nRST => nRST,
		MemProgAddr => memProgAddr,
		MemProgData => memProgData
	);

	-- Instantiation of code memory / program memory
	mprog: MemProgSuma
	port map (
		MemProgAddr => memProgAddr,
		MemProgData => memProgData
	);

	CLKPROCESS: process
	begin
	while (not finSimu) loop
		clk <= '0';
		wait for CLKPERIOD/2;
		clk <= '1';
		wait for CLKPERIOD/2;
	end loop;
	wait;
	end process;

	-- Main process to active signals.
	-- Only it is neccessary to active reset. The rest of test bank
	-- changes with the initial values of the memories, which compose the program to be executed.
	
	StimProc: process
	begin
		nRST <= '0'; -- Reset starts active
		wait for CLKPERIOD*2;
		nRST <= '1'; -- It is deactivated the reset and the execution starts
		wait for CLKPERIOD*10;
		finSimu <= true; -- If the simulation has more of 10 instructions
		-- It is neccessary to wait more before killing the simulation
		wait; -- We don't do anything with the reset
	end process;

end Test;
