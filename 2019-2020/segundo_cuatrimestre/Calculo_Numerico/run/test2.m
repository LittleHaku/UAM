A=[sqrt(3)/2 -1/2 0;1/2 sqrt(3)/2 0;0 0 1];

n = length(A);

tol = 0.001;

error = tol + 1;

x0 = [1;zeros(n-1,1)];
while error > tol

x1 = A*x0;
x1 = x1/norm(x1); error = norm(x1-x0); x0 = x1;

end
lambda = dot(x1,A*x1); 