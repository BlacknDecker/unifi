syms x
runge = 1/(1+x^2);
a = -6;
b = 6;

% tabella di comodo per mostrare le stime dell'errore
Table = cell2table(cell(0,2));
Table.Properties.VariableNames = {'x' 'error'};

n = 2;
while n<=40
    xi = ceby(n, a, b);
    fi = zeros(length(xi), 1);

    for i = 1:length(xi)
        fi(i) = subs(runge, xi(i));
    end

    p = @(x) lagrange(xi, fi, x);

    fplot(runge, [a, b])
    hold on
    fplot(p, [a, b])
    plot(xi, fi, 'r*')
    hold off
    print('-dpng', strcat(num2str(n), '.png'));

    d = abs(diff(runge, n + 1));
    % non ci sono function predefinite per trovare il massimo
    % uso una scritta da me, anche se terribilmente inefficiente
    [x, fnorm] = maximum(d, a, b, 0.01);
    error = fnorm / (factorial(n+1) * 2^n);

    iteration = {n, double(error)};
    Table = [Table; iteration];

    n = n + 2;
end

% mostra la tabella
uitable('Data',Table{:,:},'ColumnName',Table.Properties.VariableNames,...
    'RowName',Table.Properties.RowNames,'Units', 'Normalized', 'Position',[0, 0, 1, 1]);

function [x, max] = maximum(f, a, b, step)
    max = -Inf;
    x = 0;
    for i = a:step:b
        if subs(f, i) > max
            max = subs(f, i);
            x = i;
        end
    end
end
