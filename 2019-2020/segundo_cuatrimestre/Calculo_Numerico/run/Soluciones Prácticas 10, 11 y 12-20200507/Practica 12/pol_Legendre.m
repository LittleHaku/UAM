function y = pol_Legendre(n,x0)

if n == 0
    y = 1;
elseif n == 1
    y = x0;
else
    y = ((2*n+1)*x0.*pol_Legendre(n-1,x0) - n*pol_Legendre(n-2,x0))/(n+1);
end
