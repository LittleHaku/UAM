function [x, L, U] = pivote_Gauss_LU_ecuacion(A, b)
    [n m] = size(A);
    L = eye(n);
    U = A;
    x = b;
    for i = 1:1:m
        %max abs pivot
        ind = i;
        for j = i+1:1:n
            if abs(U(i, ind)) < abs(U(i, j))
                ind = j;
            end
        end
        s = U(i, :);
        U(i, :) = U(ind, :);
        U(ind, :) = s;

        s = b(i);
        b(i) = b(ind);
        b(ind) = s;
        %Gauss
        for j = i+1:1:n
            L(j, i) = U(j, i)/U(i, i);
            U(j, :) = U(j, :) - L(j, i)*U(i, :);
            x(j) = x(j) - L(j, i)*x(i);
        end
    end
    
    %Solving Ux = b'
    for i = n:-1:1
        Sum = 0;
        for j = i+1:1:m
            Sum = Sum + U(i, j)*x(j);
        end
        x(i) = (x(i) - Sum) / U(i, i);
    end

end

