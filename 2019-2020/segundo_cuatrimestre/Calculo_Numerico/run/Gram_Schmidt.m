function [Q R] = Gram_Schmidt(A)
    
    %n = m for iterative method
    [n m] = size(A);
    
    Q = zeros(n, m);
    R = zeros(m, m);
    
    for i = 1:1:m
       Q(:, i) = A(:, i);
       for j = 1:1:i-1
           R(j, i) = A(:, i)'*Q(:, j);
           Q(:, i) = Q(:, i) - R(j, i)*Q(:, j);
       end
       R(i, i) = norm(Q(:, i));
       Q(:, i) = Q(:, i) / R(i, i);        
    end
end

