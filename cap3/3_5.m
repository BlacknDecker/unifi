A = [1 -1 2 2; -1  5  -14 2; 2 -14 42 2; 2 2 2 65];

%evito di imbrogliarmi e fattorizzare due volte la stessa matrice
A3 = A;
A4 = A;

x3 = [3.1416  2.7183  1.4142  1.7320 ]';
x4 = [2.2360  2.6457  3.1416  3.3166 ]';

b3 = A3 * x3;
b4 = A4 * x4;

% esercizio 3, fattorizzazione LDL^T
x3_sol = sol_es_3(algoritmo_3_6(A3), b3);
cond_A_3_2 = cond(A3, 2);
r3 = A*x3_sol - b3;
r_b_3 = (r3'*r3) / (b3'*b3);
err_3 = ((x3_sol - x3)'*(x3_sol - x3))/(x3_sol'*x3_sol);

% esercizio 4, fattorizzazione LU con pivoting parziale
[A4, p4] = algoritmo_3_7(A4);
x4_sol = sol_es_4(A4, p4, b4);
cond_A_4_2 = cond(A4, 2);
r4 = A*x4_sol - b4;
r_b_4 = (r4'*r4) / (b4'*b4);
err_4 = ((x4_sol - x4)'*(x4_sol - x4))/(x4_sol'*x4_sol);

% fattorizzazione LU con pivot
function [A, p] =  algoritmo_3_7(A)
    [m,n]=size(A);
    if m~=n
        error('matrice non quadrata');
    end
    P=[1:n];
    for i=1:(n-1)
        [aki, ki] = max(abs(A(i:n, i)));
        if aki==0
            error('matrice singolare');
        end
        ki=ki+i-1;
        if ki>i
            A([i, ki], :) = A([ki, i], :);
            P([i, ki]) = P([ki, i]);
        end
        A((i+1):n, i) = A((i+1):n, i)/A(i, i);
        A((i+1):n, (i+1):n) = A((i+1):n, (i+1):n) -A((i+1):n, i)*A(i,(i+1):n);
    end
end

%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%55
%%%% da non far vedere
function [A] = algoritmo_3_6(A)
    [m,n]=size(A);
    if A(1,1)<=0
      error("aaaaaaaaaaaaa");
    end
    A(2:n,1) = A(2:n,1)/A(1,1);
    for j=2:n
        v = ( A(j,1:(j-1))').*diag(A(1:(j-1),1:(j-1)));
        A(j,j) = A(j,j)-A(j,1:(j-1))*v;
        if A(j,j)<=0
          error("aaaaaaaaaaaaa");
        end
        A((j+1):n,j) = (A((j+1):n,j)-A((j+1):n,1:(j-1))*v)/A(j,j);
    end
end

function [b] = sol_es_4(A, p, b)
    % costruisco matrice permutazioni
    P=zeros(length(A));
    for i=1:length(A)
        P(i, p(i)) = 1;
    end
    % risolvo sottosistemi di matrici L ed U
    b = triangolareInferioreColonne(tril(A,-1)+eye(length(A)), P*b);
    b = triangolareSuperioreColonne(triu(A), b);
end

function [b] = sol_es_3(A, b)
    b = sist_triang_inf(tril(A,-1)+eye(length(A)), b);
    b = diagonale(diag(A), b);
    b = sist_triang_sup((tril(A,-1)+eye(length(A)))',b);
end

function [d] = diagonale(d,b)
    n = size(d);
    for i = 1:n
        d(i) = b(i)/d(i);
    end
end

function [b] = sist_triang_sup(A, b)
    for j=length(A):-1:1
        b(j)=b(j)/A(j,j);
        for i=1:j-1
            b(i)=b(i)-A(i,j)*b(j);
        end
    end
end

% per colonna
function [b] = sist_triang_inf(A, b)
    for j=1:length(A)
        if A(j,j)==0
            error("Matrice singolare")
        else
            b(j)=b(j)/A(j,j);
        end
        for i=j+1:length(A)
            b(i)=b(i)-A(i,j)*b(j);
        end
    end
end
