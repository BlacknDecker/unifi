% esempio con f(x) = pi^x
xi = [0,1,2];
fi = [1, 3.14, 9.87];
x = 1.5;

y = newton(xi, fi, x);

%
% input:
%   xi - vettore dei punti di ascissa
%   fi - vettore dei valori di f(x)
%   x  - punto in cui calcolare f(x)
% output:
%   y  - valore di f(x)
%
function y = newton(xi, fi, x)
    dd = diff_div(xi, fi);
    y = dd(length(dd));
    for k = length(dd)-1:-1:1
        y = y*(x-xi(k))+dd(k);
    end
end

function [fi] = diff_div(xi, fi)
    for i = 1:length(xi) - 1
        for j = length(xi):-1:i+1
            fi(j) = (fi(j) - fi(j-1))/(xi(j)-xi(j-i));
        end
    end
end
