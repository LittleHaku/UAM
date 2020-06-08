function [lambda] = metodo_potencia2(A, x1)
    [n m] = size(A);
    
    x0 = zeros(n, 1);
    %x1 = ones(n, 1);
    
    it = 0;
    it_max = 50000;
    eps = 1e-6;
    while it < it_max && abs(norm(x0 - x1/norm(x1, 2))) >= eps
        x1 = x1/norm(x1, 2);
        x0 = x1;
        x1 = A*x0;
        
        
        it = it +1;
    end
it
    lambda = (x1'*A*x1)/(x1'*x1);
    
end

