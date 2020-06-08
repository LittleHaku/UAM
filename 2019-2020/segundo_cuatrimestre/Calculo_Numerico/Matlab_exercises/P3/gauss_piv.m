function x = gauss_piv(A, b)
d = length(A);
%change the pivot with the highest absolute maximum among lower rows


for k = 1:1:d-1
    %the maximum and the index of the maximum
    %[m ind] = max(abs(A()))
    %{
    for i = k+1:1:d
       if abs(A(i, k)) > abs(A(k, k))
           c = A(i, :);
           A(i, :) = A(k, :);
           A(k, :) = c;
           
           
           c2 = b(i);
           b(i) = b(k);
           b(k) = c2;
       end
    end
    %}
    
    for i = k+1:1:d
        L = A(i, k)/A(k, k);
        A(i,:) = A(i,:)-L*A(k, :);
        b(i) = b(i) - L*b(k);

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