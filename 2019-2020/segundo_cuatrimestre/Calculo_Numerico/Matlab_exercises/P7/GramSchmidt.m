function [Q, R] = GramSchmidt(A)

[n m] = size(A);
Q = zeros(n, m);
R = zeros(m, m);
format short

for i = 1:1:m
    Q(:, i) = A(:, i);
    for j = i-1:-1:1
        R(j, i) = A(:, i)'*Q(:, j);
        Q(:, i) = Q(:, i) - R(j, i)*Q(:, j);
        
    end
    R(i, i) = norm(Q(:, i));
    Q(:, i) = Q(:, i)./norm(Q(:, i));
end



end