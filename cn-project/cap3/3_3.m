% input: matrice preparata per essere scomposta LDL^T, vettore b dei termini noti
function [b] = sol_es_3(A, b)
    b = sist_triang_inf(tril(A,-1)+eye(length(A)), b);
    b = diagonale(diag(A), b);
    b = sist_triang_sup((tril(A,-1)+eye(length(A)))',b);
end

function [d] = diagonale(d, b)
    n = size(d);
    for i = 1:n
        d(i) = b(i)/d(i);
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
