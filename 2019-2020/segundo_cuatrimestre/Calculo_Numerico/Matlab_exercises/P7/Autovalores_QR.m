
A = [1 4 1;
    2 1 0;
    -1 3 1];

[n m] = size(A);

it = 0;
max_it = 100;
eps = 1e-6;
m = 1;

while it < max_it & m >= eps
    
    [Q R] = GramSchmidt(A);
    A = R*Q;
    
    m = A(2, 1);

    for i = 1:1:m-1
        m = max(m, max(abs(A(i+1:1:n, i))));
    end
    
    it = it+1;
end


A
eig(A)



