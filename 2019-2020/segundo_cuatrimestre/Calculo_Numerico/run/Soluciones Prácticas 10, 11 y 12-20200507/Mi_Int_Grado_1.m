function [I] = Mi_Int_Grado_1(X, Y, x)
% Esta función calcula la interpolación a trozos de grado 1 con nodos X=(x_1, ..., x_n), Y=(y_1, ..., y_n)
% en el punto x, con x_1<=x<=x_n

n = length(X);
if x < X(i) || x > X(n)
   I = NaN;
   return;
end
i = 1;
while X(i) <= x
   i = i + 1;
end
I = Y(i-1) + (x - X(i-1)) * (Y(i) - Y(i-1)) / (X(i) - X(i-1));

end