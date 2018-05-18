tol = 10^(-5);

for n = 100:20:1000
    A = matr_sparsa_es1(n);
    b = ones(n, 1);
    x0 = zeros(n, 1);
    [x, i] = jacobi(A, b, x0, tol);

    plot(n, i, 'r.')
    hold on
end

function [x, i] = jacobi(A, b, x0, tol)
    D = diag(diag(A));
    J = - inv(D) * (A-D);
    q = D \ b;
    x = J*x0 + q;
    i = 1;
    err = norm(x-x0)/norm(x);
    while err > tol
        x0 = x;
        x = J*x0 + q;
        err = norm(x-x0)/norm(x);
        i = i + 1;
    end
end
