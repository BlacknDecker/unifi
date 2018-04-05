% input: alpha di cui calcolare la radice quadrata,
%        punto di innesco
%        numero max iterazioni consentite
%        tolleranza richiesta
%        Tabella in cui segnarsi i parametri dell'esecuzione
function [T, sqrt_alpha] = newton_sqrt(alpha, x0, imax, tolx, T)
    sqrt_alpha = (x0 + alpha/x0) / 2;
    i = 1;
    row = {i, sqrt_alpha, abs( sqrt(alpha) - sqrt_alpha )};
    T = [T; row];

    while(i < imax) && (abs(sqrt_alpha-x0) > tolx)
        x0 = sqrt_alpha;
        i = i+1;
        sqrt_alpha = (x0 + alpha/x0) / 2;
        row = {i, sqrt_alpha, abs( sqrt(alpha) - sqrt_alpha )};
        T = [T; row];
    end
end
