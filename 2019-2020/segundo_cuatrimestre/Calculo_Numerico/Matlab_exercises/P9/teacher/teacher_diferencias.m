function c = diferencias(x,y)
% Calculo de los coeficientes del polinomio de interpolacion en la forma de
% Newton, donde x son los nodos e y los valores

n = length(y);

c = y; % Inicializamos los coeficientes como y

for k = 2:n
    for i = n:-1:k
        c(i) = (c(i) - c(i-1))/(x(i) - x(i-k+1)); % Calculamos las diferencias divididas
    end
end
