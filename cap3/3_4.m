% input: matrice preparata per la scomposizione LU, vettore delle permutazioni, vettore dei termini noti
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
