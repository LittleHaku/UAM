----------------------------------------------------------------------
-- File: RegsMIPSTb.vhd
-- Description: Testbench of the register bank of the microprocessor MIPS
-- Date last modification: 2019-02-22

-- Authors: Sofía Martínez García (2019),Alberto Sánchez (2012), Ángel de Castro (2011) 
-- Sbuject: C.E. 1st grade
-- Laboratory group:
-- Theory group:
-- Task: 2
-- Exercise: 1
----------------------------------------------------------------------

library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity RegsMIPSTb is
end RegsMIPSTb;

architecture Practica of RegsMIPSTb is

component RegsMIPS
	port (
		Clk : in std_logic; -- Clock
		NRst : in std_logic; -- Asycrhonous reset in low level
		A1 : in std_logic_vector(4 downto 0); --  Address for the port Rd1
		Rd1 : out std_logic_vector(31 downto 0); -- Data of the port Rd1
		A2 : in std_logic_vector(4 downto 0); -- Address for the port Rd2
		Rd2 : out std_logic_vector(31 downto 0); -- Data of the port Rd2
		A3 : in std_logic_vector(4 downto 0); -- Address for the port Wd3
		Wd3 : in std_logic_vector(31 downto 0); -- Input data Wd3
		We3 : in std_logic -- Enable of the register bank
	);
	end component;

	-- Constants
	constant TAMANO   : integer := 32;
	constant INSTANTE : time := 10 ns;
	constant PERIOD   : time := 50 ns;       -- Signal clock period

	-- Signals
	signal rd1, rd2, wd3 : std_logic_vector(31 downto 0):=(others=>'0');
	signal a1, a2, a3 : std_logic_vector(4 downto 0):="00000";
	signal clk, we3, nRst : std_logic;
	signal endSim : boolean := false;    -- Signal to stop the clock

begin  -- Task

	-- Instantiation of the register bank
	UUT : RegsMIPS
	port map (
		Clk => clk,
		NRst=> nRst,
		A1 => a1,
		Rd1 => rd1,
		A2 => a2,
		Rd2 => rd2,
		A3 => a3,
		Wd3 => wd3,
		We3 => we3
	);


	-- Clock process. This process stops when the signal endSim is TRUE
	process
	begin
		while not endSim loop
			clk <= '0';
			wait for PERIOD/2;
			clk <= '1';
			wait for PERIOD/2;
		end loop;
		wait;
	end process;

	-----------------------------------------------------------------------------
	-- Main process of tests
	-----------------------------------------------------------------------------
	process
	begin
		---------------------------------------------------------------------------
		-- Reset test
		-- All the register should have the value 0.
		---------------------------------------------------------------------------
		
		-- Firstly, it is tested to reset while applying for writing.
		
		we3 <= '1';
		a1 <= (others=>'0');
		a2 <= (others => '0');
		a3 <= std_logic_vector(to_unsigned(2,5));
		wd3 <= (others => '1');
		nRst <= '0';

		wait until rising_edge(clk);
		
		-- and it is checked that nothing has not been written in any register.

		for i in 0 to TAMANO-1 loop
			A1 <= std_logic_vector(to_unsigned(i,5));
			wait for INSTANTE;
			-- To check the output of RS1
			assert rd1 = x"00000000"
			report "Reset error with active writing"
			severity FAILURE;
		end loop;  -- i

		-- All signals have been initialized to zero and then restet to 1

		we3 <= '0';
		a1 <= (others=>'0');
		a2 <= (others => '0');
		a3 <= (others => '0');
		wd3 <= (others => '0');
		nRst <= '0';
		wait for INSTANTE;
		nRst <= '1';

		-- It is checked again that all the registers have the value 0.

		for i in 0 to TAMANO-1 loop
			A1 <= std_logic_vector(to_unsigned(i,5));
			wait for INSTANTE;
			-- Check the output of RS1
			assert rd1 = x"00000000"
			report "Reset Error"
			severity FAILURE;
		end loop;  -- i

		---------------------------------------------------------------------------
		-- Writing test
		-- In every register it is written the vlue of its address +16
		---------------------------------------------------------------------------
		for i in 0 to TAMANO-1 loop
			-- We put the data to save
			wd3 <= std_logic_vector(to_unsigned(i+16,32));
			-- We put the destination address
			a3 <= std_logic_vector(to_unsigned(i,5));
			-- We point out what we want to write
			we3 <= '1';
			-- We wait a falling edge clock, where the writing is done
			
			wait until rising_edge(clk);
		end loop;  -- i

		---------------------------------------------------------------------------
		-- Reading test. It is read by rd1 and rd2 at the same time
		-- With Rd1 the memory is covered in upward direction
		-- With Rd2 in downward direction
		---------------------------------------------------------------------------
		we3 <= '0';
		wait for INSTANTE;

		for i in 1 to TAMANO-1 loop
			-- in a1 we put the upward addresses.
			a1 <= std_logic_vector(to_unsigned(i,5));

			-- in a2 we put the downward addresses.
			a2 <= std_logic_vector(to_unsigned(32-i,5));

			wait for INSTANTE;
			
			-- check the output of rd1
			assert rd1 = std_logic_vector(to_unsigned(i+16,32))
			report "Reading error of rd1"
			severity FAILURE;

			-- Check the output of RS2
			assert rd2 = std_logic_vector(to_unsigned((32-i)+16,32))
			report "Reading error of rd2"
			severity FAILURE;
		end loop;  -- i

		-- Check that in the zero register is read '0'
		a1 <= (others=>'0');
		a2 <= (others => '0');
		wait for INSTANTE;

		--  Check the output of rd1
		assert rd1 = std_logic_vector(to_unsigned(0,32))
		report "Error. Reading the 0 register, in RS1 it is not obtained 0"
		severity FAILURE;

		--  Check the output of rd2
		assert rd2 = std_logic_vector(to_unsigned(0,32))
		report "Error. Reading the 0 register, in RS2 it is not obtained 0"
		severity FAILURE;

		-- Check that rd1 and rd2 change when the value of the register is changed
		-- but we use the same address.
		
		for i in 1 to TAMANO-1 loop

			a1 <= std_logic_vector(to_unsigned(i,5));
			a2 <= std_logic_vector(to_unsigned(i,5));
			a3 <= std_logic_vector(to_unsigned(i,5));
			wait for INSTANTE;

			-- Check if the value of the register changes if the address is not modified.
			wd3 <= x"0f0f0f0f";
			we3 <= '1';
			wait until rising_edge(clk);
			wait for INSTANTE;

			assert rd1 = x"0f0f0f0f" 
			report "Error. Rd1 output does not change when the content of the register changed but not the address."
			severity FAILURE;  

			assert rd2 = x"0f0f0f0f" 
			report "Error. Rd2 output does not change when the content of the register changed but not the address."
			severity FAILURE;  

		end loop;
		
		-- To check the writing in R0
		we3 <= '1';
		wd3 <= x"fabada00";
		a3 <= "00000";
		a1 <= "00000";
		wait until rising_edge(clk);
		wait for INSTANTE;
		assert rd1 = x"00000000"
		report "Error. The writing is not being blocked in R0"
		severity FAILURE;
		
		-- To check the Write Enable
		we3 <= '1';
		wd3 <= x"bebecafe";
		a3 <= "00001";
		a1 <= "00001";
		wait until rising_edge(clk); --Writing to obtain a known value in R1
		wait for INSTANTE;
		we3 <= '0';
		wd3 <= x"fabada00";
		wait until rising_edge(clk);
		wait for INSTANTE;
		assert rd1 = x"bebecafe"  -- We check that R1 does not change if We3='0'
		report "Error. Write Enable is not working"
		severity FAILURE;
		
		
		

		report "Finished simulation. Check if previously errors have been generated" severity note;
		endSim<=TRUE;
		wait;
	end process;

end Practica;
