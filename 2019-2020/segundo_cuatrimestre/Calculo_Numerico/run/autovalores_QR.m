function [A] = f(A)
    [n m] = size(A);
    
    it = 0;
    it_max = 100;
    max_ele = 1;
    eps = 1e-6;
    
    while it < it_max && max_ele >= eps
        
       [Q R] = Gram_Schmidt(A);
       A = R*Q;
        
       max_ele = 0;
       for i = 1:1:m-1
           max_ele = max(max_ele, abs(norm(A(i+1:1:n, i), inf)));
       end
       
        it = it + 1;
    end
    
end

