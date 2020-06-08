function y = pol_Chebyshev(n,x0)

if n == 0
    y = 1;
elseif n == 1
    y = x0;
else
    y = 2*x0.*pol_Chebyshev(n-1,x0) - pol_Chebyshev(n-2,x0);
end

%y = cos(n*acos(x0));

