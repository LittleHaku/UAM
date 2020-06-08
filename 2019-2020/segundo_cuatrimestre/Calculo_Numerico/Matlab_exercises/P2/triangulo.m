function [P, A] = triangulo(a, b, c)

P = a + b + c
s = P / 2
A = sqrt(s*(s-a)*(s-b)*(s-c))

end