function [L, U] = f(A, b)
    
    %n = m for iterative method
    [n m] = size(A);
    
    x0 = zeros(m, 1);
    x1 = x0;
    it = 0;
    eps = 1e-6;
    tol = 1;
    while it < 50 && tol >= eps
        for i = 1:1:n
            Sum = 0;
		%Gauss - Seidel
	%for j = 1:1:i-1
         %       Sum = Sum + x1(j)*A(i, j);
          %  end
           % for j = i+1:1:n
            %    Sum = Sum + x0(j)*A(i, j);
            %end
            %jacobi
            for j = 1:1:n
                if i == j
                    continue
                end
                Sum = Sum + x0(j)*A(i, j);
            end
            x1(i) = (-Sum + b(i)) / A(i, i);
        end
        tol = norm(x1 - x0);
        x0 = x1;
        it = it + 1;
    end
    b
    A*x1
end

