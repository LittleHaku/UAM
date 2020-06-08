A = [1 2 0;
    1 -2 1;
    1 1 0;
    1 1 -1];
b = [1;
    1;
    1;
    -2]


%Calcular QR
[Q,R] = Gram_Schmidt(A);
Q
R
Q = transpose(Q);

b_new = Q*b;

%Calcular Rx = b_new
[m,n] = size(A);
x = zeros(n,1);

for i = [n:-1:1]
    x(i) = (b_new(i) - R(i,i+1:n)*x(i+1:n))/R(i,i);
end

display("La solucion es: ")
disp(x)
display("El error cometido es: ")
disp(norm(A*x-b))