
A = [1 4 1;
    2 1 0;
    -1 3 1];

[n m] = size(A);

x1 = zeros(m, 1);
x0 = x1;

for i = 1:1:m
    x1(i) = 1;
end


it = 0;
eps = 1e-6;
max_it = 1000;

while it < max_it & norm(x1/norm(x1, inf)-x0) >= eps
    
    x1 = x1/norm(x1, inf);
    x0 = x1;
    x1 = A*x0;
    
    it = it + 1;
end

it
(x0'*x1)/(x0'*x0)
%norm(x1, inf) %Otra solucion





