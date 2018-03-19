function [b]= soluzione_eserczio_4(A,b)
	b = sistemaTriangolareInferiore(tril(A,-1)+eye(length(A)), b);
	b = sistemaTriangolareSuperiore(triu(A), b);
end
