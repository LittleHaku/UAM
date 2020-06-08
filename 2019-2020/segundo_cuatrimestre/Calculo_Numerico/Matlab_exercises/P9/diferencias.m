function [c] = diferencias(x, y)
format long

%c = 0;
n = length(x);
%mat = zeros(n, n);

%for i = 1:1:n
%    mat(i, 1) = y(i);
%end
%x(j-i+1) is the node of the left diagonal <- ^
%for i = 2:1:n
%    for j = i:1:n
%        mat(j, i) = (mat(j, i-1) - mat(j-1, i-1)) / (x(j) - x(j-i+1));
%    end
%end

%mat
%c = diag(mat)';
c = y;
for i = 2:1:n
    for j = n:-1:i
        c(j) = (c(j) - c(j-1))./(x(j) - x(j-i+1));
    end
end
end

