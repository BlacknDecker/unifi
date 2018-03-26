Table = cell2table(cell(0,4)); % modo veloce per iniz tab vuota
Table.Properties.VariableNames = {'x' 'j' 'h' 'phi_h'};

x = 1;
for j = 1:10
   h = 10^(-j);
   iteration = {x, j, h, phi_h(x, h)};
   Table = [Table; iteration];
end

% rappresentazione grafica
uitable('Data',Table{:,:},'ColumnName',Table.Properties.VariableNames,...
    'RowName',Table.Properties.RowNames,'Units', 'Normalized', 'Position',[0, 0, 1, 1]);



% definizioni funzioni
function y = f(x)
    y = x^4;
end

function y = phi_h(x, h)
    y = (f(x+h) - f(x-h))/(2*h);
end
