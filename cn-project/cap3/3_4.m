% input: matrice preparata per la scomposizione LU, vettore delle permutazioni, vettore dei termini noti

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
