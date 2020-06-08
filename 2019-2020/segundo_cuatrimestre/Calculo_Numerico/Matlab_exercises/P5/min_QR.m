function [x] = min_QR(A, b)
A = [2 1 -1;
    0 -1 1; 
    0 1 -1; 
    2 1 1];
b = [1;2;3;4];

%A = input("matriz A")
%b = input("dato b")

[Q R] = GramSchmidt(A);

x = Q'*b
n = size(x);

for i = n:-1:1
    Sum = 0;
    for j = i+1:1:n
        Sum = Sum + R(i, j)*x(j);
    end
    x(i) = (x(i) - Sum) / R(i, i);
end

disp("El error es:")
disp(norm(A*x-b))

end