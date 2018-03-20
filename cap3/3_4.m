function [b]= sol_es_4(A,b)
	P = zeros(length(A));
	for i=1:length(A)
		P(i, p(i)) = 1;
	end
	b = sist_triang_inf(tril(A,-1)+eye(length(A)), P*b);
	b = sist_triang_sup(triu(A), b);
end
