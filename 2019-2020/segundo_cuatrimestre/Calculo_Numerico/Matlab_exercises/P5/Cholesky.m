function [C] = Cholesky(A)
A = [1 2 3;
    2 5 6;
    3 6 9];
[L U] = lu_gauss(A);

D = diag(diag(U));
n = size(U);
for i = 1:1:n
    D(i, i) = sqrt(D(i, i));
end

C = L*D;


end