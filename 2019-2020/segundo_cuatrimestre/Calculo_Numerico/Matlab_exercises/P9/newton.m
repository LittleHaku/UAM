function [y] = newton(nodos, valores, x)

n = length(nodos);
c = diferencias(nodos, valores);
y = c(n);
for i = n:-1:2
    y = y .* (x - nodos(i-1));
    y = y + c(i-1);
end


end

