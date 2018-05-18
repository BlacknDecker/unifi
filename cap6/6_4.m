tol = 10^(-5);

for n = 100:20:1000
    A = matr_sparsa_es1(n);
    b = ones(n, 1);
    x0 = zeros(n, 1);
    [x, i] = gauss_seidel(A, b, x0, tol);

    plot(n, i, 'r.')
    hold on
end


function [x, i] = gauss_seidel(A, b, x0, tol)
    D=diag(diag(A));
    L=tril(A)-D;
    U=triu(A)-D;
    DI=inv(D+L);
    GS=-DI*U;
    b1=(D+L)\b;
    x=GS*x0+b1;
    i=1;
    err = norm(x-x0,inf)/norm(x);

    while err>tol
        x0=x;
        x=GS*x0+b1;
        err = norm(x-x0,inf)/norm(x);
        i=i+1;
    end

end
