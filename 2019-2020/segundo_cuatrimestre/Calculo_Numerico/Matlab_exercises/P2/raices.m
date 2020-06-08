%calcula las raices de un polinomio a*x^2+b*x+c
a = input('Dame un numero a: ');
b = input('Dame un numero b: ');
c = input('Dame un numero c: ');

disp('raices del polinomio son');
if(a == 0)
    disp('error a = 0')
elseif(b^2-4*a*c < 0)
    disp('tiene raices imaginarias')
else
    disp((-b+sqrt(b^2-4*a*c))/(2*a));
    disp((-b-sqrt(b^2-4*a*c))/(2*a));
end