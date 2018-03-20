% input: matrice preparata per la scomposizione LU, vettore delle permutazioni, vettore dei termini noti
function [b] = sol_es_4(A, P, b)
    b = TriangolareInf(tril(A,-1)+eye(length(A)),P*b);
    b = TriangolareSup(triu(A),b);
end
