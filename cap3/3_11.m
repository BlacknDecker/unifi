x = [1/2, 1/2]';

tolx = 10^-6; % testato da 10^-3 a 10^-6

F = @(x) [2*x(1) - x(2); 3*x(2)^2 - x(1)];
J = @(x) [2, -1; -1, 6*x(2)];

[x, i, incr, err] = non_lin_newton_mod(F, J, x, 500, tolx);

function [x, i, incr, err]  = non_lin_newton_mod(F, J, x, imax, tolx)
    i = 0;
    xold = x+100;
    while (i < imax) && (norm(x-xold) > tolx)
        i = i+1;
        xold = x;
        [A, p] = algoritmo_3_7(feval(J,x));
        x = x+sol_es_4(A, p, -feval(F,x));    end
    err = norm(x-[1/12, 1/6]');
    incr = norm(x-xold);
end
