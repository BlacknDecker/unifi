x = [1/2, 1/2]';
tolx = 10^-3; % testato da 10^-3 a 10^-6

F = @(x) [2*x(1) - x(2); 3*x(2)^2 - x(1)];
J = @(x) [2, -1; -1, 6*x(2)];

[x, i, in, er] = non_lin_newton_mod(F, J, x, 500, tolx);

function [x, i, in, er] = non_lin_newton_mod(F, J, x, imx, tolx)
    i = 0;
    xold = x+100;
    while (i < imx) && (norm(x-xold) > tolx)
        i = i+1;
        xold = x;
        [A, p] = algoritmo_3_7(feval(J,x));
        x = x+sol_es_4(A, p, -feval(F,x));
    end
    er = norm(x-[1/12, 1/6]');
    in = norm(x-xold);
end

function [A, p] =  algoritmo_3_7(A)
    [m,n]=size(A);
    if m~=n
        error('matrice non quadrata');
    end
    p=[1:n];
    for i=1:(n-1)
        [aki, ki] = max(abs(A(i:n, i)));
        if aki==0
            error('matrice singolare');
        end
        ki=ki+i-1;
        if ki>i
            A([i, ki], :) = A([ki, i], :);
            p([i, ki]) = p([ki, i]);
        end
        A((i+1):n, i) = A((i+1):n, i)/A(i, i);
        A((i+1):n, (i+1):n) = A((i+1):n, (i+1):n) -A((i+1):n, i)*A(i,(i+1):n);
    end
end

function [b] = sol_es_4(A, p, b)

    % espando la memorizzazione della matrice delle permutazioni 'compressa' nel vettore
    P = zeros(length(A));
    for i=1:length(A)
        P(i, p(i)) = 1;
    end

    % risolvo sottosistemi di matrici L ed U
    b = sist_triang_inf(tril(A,-1)+eye(length(A)), P*b);
    b = sist_triang_sup(triu(A), b);
end

% accesso per colonna
function [b] = sist_triang_inf(A, b)
    for j = 1 : length(A)
        b(j) = b(j)/A(j,j);
        for i = j+1 : length(A)
            b(i) = b(i)-A(i,j)*b(j);
        end
    end
end

% accesso per colonna
function [b] = sist_triang_sup(A, b)
    for j = length(A) : -1 : 1
        b(j)=b(j)/A(j,j);
        for i = 1 : j-1
            b(i) = b(i)-A(i,j)*b(j);
        end
    end
end
