function x = gauss(A, b)
d = length(A);
%chequer solution
%x = A^-1*b
for k = 1:1:d-1
    for i = k+1:1:d
        L = A(i, k)/A(k, k);
        A(i,:) = A(i,:)-L*A(k, :);
        b(i) = b(i) - L*b(k);
        %alternative solution
        
        %A(i, k) = A(i, k)/A(k, k);
        %for j = k+1:1:d
        %    A(i, j) = A(i, j) - A(i, k)*A(k, j);
        %end
        %b(i) = b(i) - A(i, k)*b(k);

        %A(i, k) = 0;
    end
end

%Now A is upper triangular and the b data is updated

for i = d:-1:1
    for j = i+1:1:d
        b(i) = b(i) - b(j)*A(i, j);
    end
    
    b(i) = b(i) / A(i, i);
end

x = b;
end