function [x, err] = Mi_Biseccion(f, a, b, n)
% Esta funci?n aproxima un cero de la funci?n f en [a,b] con f(a)f(b)<0
% usando el m?todo de bisecci?n con n iteracciones.
% La funci?n f debe ser de tipo "function handle".

for i = 1:n
   x = (a + b)/2;
   err = (b - a)/2;
   if f(x)==0
      err = 0;
      return
   elseif f(a)*f(x)<0
      b = x;
   else
      a = x;
   end
end

end