

library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;
use ieee.std_logic_arith.all;

entity pcounter is
	port(
		PCviejo : in std_logic_vector(31 downto 0);
		PCretrasado : out std_logic_vector(31 downto 0);
		we : in std_logic;
		clk :in std_logic;
		nrst : in std_logic
	);
end entity;

architecture pcounterarch of pcounter is

begin

	process(clk, nrst)
	begin
		if nrst = '0' then
			pcretrasado <= (others => '0' );
		elsif rising_edge(clk) and we = '1' then
			pcretrasado <= pcviejo;
		end if;	
	end process;

end architecture;