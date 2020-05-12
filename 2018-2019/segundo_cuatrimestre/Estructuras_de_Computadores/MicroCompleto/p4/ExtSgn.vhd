
library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;
use ieee.std_logic_arith.all;

entity ExtSgn is
	port(
		entradacortasgn : in std_logic_vector(15 downto 0);
		salidalargasgn : out std_logic_vector(31 downto 0)
	);

end entity;

architecture Extsgnarch of extsgn is
begin

salidalargasgn(15 downto 0) <= entradacortasgn;
salidalargasgn(31 downto 16) <= (others => entradacortasgn(15));

end architecture;