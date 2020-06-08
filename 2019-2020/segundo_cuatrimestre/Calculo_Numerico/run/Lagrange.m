function [y] = Lagrange(a, b, x)

y = 0;
[m n] = size(a);

for i = 1:n
    mul = b(i);
    for j = 1:n
        if i == j
            continue
        end
        mul = mul .* (x-a(j))./(a(i)-a(j));
    end
    y = y + mul;
end




end

