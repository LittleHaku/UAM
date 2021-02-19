onerror {resume}
quietly WaveActivateNextPane {} 0
add wave -noupdate /processor_tb/i_processor/Clk
add wave -noupdate /processor_tb/i_processor/Reset
add wave -noupdate -divider PC
add wave -noupdate /processor_tb/i_processor/PC_reg
add wave -noupdate /processor_tb/i_processor/PC_next
add wave -noupdate /processor_tb/i_processor/IF_PC_plus4
add wave -noupdate /processor_tb/i_processor/ID_PC_plus4
add wave -noupdate /processor_tb/i_processor/EX_PC_plus4
add wave -noupdate /processor_tb/i_data_mem/memo
add wave -noupdate -divider Instruction
add wave -noupdate /processor_tb/i_processor/IF_Instruction
add wave -noupdate /processor_tb/i_processor/ID_Instruction
add wave -noupdate -divider regs
add wave -noupdate -expand /processor_tb/i_processor/RegsMIPS/regs
add wave -noupdate -divider Dato_Inmediato
add wave -noupdate /processor_tb/i_processor/ID_Inm_ext
add wave -noupdate /processor_tb/i_processor/EX_Inm_ext
add wave -noupdate -divider data
add wave -noupdate /processor_tb/i_processor/reg_RD_data
add wave -noupdate /processor_tb/i_processor/reg_RD_data
add wave -noupdate /processor_tb/i_processor/EX_AluControl
add wave -noupdate /processor_tb/i_processor/Alu_Op2
add wave -noupdate /processor_tb/i_processor/EX_Alu_Res
add wave -noupdate /processor_tb/i_processor/MEM_Alu_Res
add wave -noupdate /processor_tb/i_processor/WB_Alu_Res
add wave -noupdate /processor_tb/i_processor/WB_dataIn_Mem
add wave -noupdate /processor_tb/i_processor/MEM_dataIn_Mem
add wave -noupdate -divider Destino
add wave -noupdate /processor_tb/i_processor/ID_reg_RD
add wave -noupdate /processor_tb/i_processor/EX_reg_RD
add wave -noupdate /processor_tb/i_processor/ID_reg_RT_Dest
add wave -noupdate /processor_tb/i_processor/EX_reg_RT_Dest
add wave -noupdate /processor_tb/i_processor/EX_reg_Dest
add wave -noupdate /processor_tb/i_processor/MEM_reg_Dest
add wave -noupdate /processor_tb/i_processor/WB_reg_Dest
add wave -noupdate -divider src
add wave -noupdate /processor_tb/i_processor/ID_reg_RS
add wave -noupdate /processor_tb/i_processor/EX_reg_RS
add wave -noupdate -divider temporal
add wave -noupdate /processor_tb/i_processor/ID_reg_RT
add wave -noupdate /processor_tb/i_processor/EX_reg_RT
add wave -noupdate /processor_tb/i_processor/MEM_reg_RT
TreeUpdate [SetDefaultTree]
WaveRestoreCursors {{Cursor 1} {911 ns} 0}
quietly wave cursor active 1
configure wave -namecolwidth 286
configure wave -valuecolwidth 185
configure wave -justifyvalue left
configure wave -signalnamewidth 0
configure wave -snapdistance 10
configure wave -datasetprefix 0
configure wave -rowmargin 4
configure wave -childrowmargin 2
configure wave -gridoffset 0
configure wave -gridperiod 1
configure wave -griddelta 40
configure wave -timeline 0
configure wave -timelineunits ns
update
WaveRestoreZoom {241 ns} {529 ns}
