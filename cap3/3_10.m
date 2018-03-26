function [x] = non_lin_newton(F,J, x, imax, tolx)
    i = 0;
    xold = x + 100; % per essere sicuri
    while (i < imax) && (norm(x-xold) > tolx)
        i = i+1;
        xold = x;
        [A, p] = algoritmo_3_7(feval(J,x));
        x = x+sol_es_4(A, p, -feval(F,x));
    end
end
