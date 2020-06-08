A = [13 -1 4; -2 7 4; 5 3 -9];
b = [5;3;2];

%tolerancia
tol = 0.001;
itmax = 50;
it = 0;

d = length(A);


x0 = zeros(d, 1);
x1 = zeros(d, 1);
error = tol+1; %para que entre 1 iteracion en el bucle

while it < itmax && error >= tol
    for i = 1:1:d
        Sum1 = 0;
        for j = 1:1:i-1
           Sum1 = Sum1 + A(i, j)*x1(j);
        end
        Sum2 = 0;
        for j = i+1:1:d
            Sum2 = Sum2 + A(i, j)*x0(j);
        end
        x1(i) = (b(i) - Sum1 - Sum2)/A(i, i);
        
    end
    
    error = norm(x1-x0);
    x0 = x1;
    
    it = it + 1;
end

if it == itmax
    disp("se ha alcanzado el maximo de iteraciones");
else
    disp("num de iteraciones");
    disp(it);
end
disp("el error es: "); 
disp(error);
disp("la sol aprox es");
disp(x1);
%chequer A*x1

A*x1

