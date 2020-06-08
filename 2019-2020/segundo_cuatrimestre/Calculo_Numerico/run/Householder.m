function [Q R] = f(A)
    
    %n = m for iterative method
    [n m] = size(A);
    R = A;
    Q = eye(n);
    
    for i = 1:1:min(n, m) -1
        w = R(i:n, i);
        if w(1) > 0
            w(1) = w(1) + norm(w);
        else
            w(1) = w(1) - norm(w);
        end
        
        w = w / norm(w);
        
        P = eye(n-i+1) - 2*w*w';
        R(i:n, i:m) = P * R(i:n, i:m);
        Q(i:n, :) = P*Q(i:n, :);
    end
    Q = Q';
    
end

