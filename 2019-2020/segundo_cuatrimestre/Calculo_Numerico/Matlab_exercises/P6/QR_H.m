function [Q,R] = QR_H(A)
%metodo QR con el algoritmo de Householder
A = [12 -51 4; 6 167 -68; -4 24 -41];
A = [12 -51 4; 6 167 -68];
format rat

[n, m] = size(A);
R = A

Q = eye(n)

for i = 1:1:min(n, m)-1
   w = R(i:n, i);
   if R(i, i) > 0
       w(1) = w(1) + norm(R(i:n, i));
   else
       w(1) = w(1) - norm(R(i:n, i));
   end
   w = w./norm(w);
   P = eye(n-i+1) - 2*w*w';
   Q(i:n, :) = P*Q(i:n, :);
   R(i:n, i:m) = P*R(i:n, i:m);
end


Q'*R
A
Q'*R == A
norm(A-Q'*R)

end