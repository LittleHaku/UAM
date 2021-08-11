<?php
$f = fopen( 'php://stdin', 'r' );
stream_set_blocking($f, false);

echo "Inicio\n";
echo "Script PHP\n\n";

echo "\n\nRecibido por ARGV:\n";
foreach($argv as $value)
{
  echo "$value\n";
}
echo "Fin de datos\n";


fclose( $f );
echo "\n\nFin del script\n";
?>