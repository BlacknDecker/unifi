tol = 10^(-5);
n = 1000;
A = matr_sparsa_es1(n);
b = ones(n, 1);
[x_gs, i_gs, err_gs] = gauss_seidel_err(A, b, zeros(n, 1), tol);
[x_j, i_j, err_j] = jacobi_err(A, b, zeros(n, 1), tol);

semilogy(1:i_gs, err_gs)
hold on
semilogy(1:i_j, err_j)

function [x, i, err] = gauss_seidel_err(A, b, x0, tol)
    D=diag(diag(A));
    L=tril(A)-D;
    U=triu(A)-D;
    DI=inv(D+L);
    GS=-DI*U;
    b1=(D+L)\b;
    x=GS*x0+b1;
    i=1;
    err(i) = norm(x-x0,inf)/norm(x);

    while err(i) > tol
        x0=x;
        x=GS*x0+b1;
        i=i+1;
        err(i) = norm(x-x0,inf)/norm(x);
    end

end


function [x, i, err] = jacobi_err(A, b, x0, tol)
    D = diag(diag(A));
    J = - inv(D) * (A-D);
    q = D \ b;
    x = J*x0 + q;
    i = 1;
    err(i) = norm(x-x0)/norm(x);
    while err > tol
        x0 = x;
        x = J*x0 + q;
        i = i + 1;
        err(i) = norm(x-x0)/norm(x);
    end
end
