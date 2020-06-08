function [L U] = lu_gauss(A)
d = length(A);

L = eye(d);

for k = 1:1:d-1
    for i = k+1:1:d
        L(i, k) = A(i, k)/A(k, k);
        A(i,:) = A(i,:)-L(i, k)*A(k, :);
    end
end

U = A;


end