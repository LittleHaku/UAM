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
add wave -noupdate -divider señales
add wave -noupdate /processor_tb/i_processor/ID_Ctrl_Jump
add wave -noupdate /processor_tb/i_processor/ID_Ctrl_MemRead
add wave -noupdate /processor_tb/i_processor/ID_Ctrl_RegWrite
add wave -noupdate /processor_tb/i_processor/EX_Ctrl_MemRead
add wave -noupdate /processor_tb/i_processor/ID_Ctrl_ALUOP
add wave -noupdate /processor_tb/i_processor/hazard_control
add wave -noupdate -divider Instruction
add wave -noupdate /processor_tb/i_processor/IF_Instruction
add wave -noupdate /processor_tb/i_processor/ID_Instruction
add wave -noupdate -divider regs
add wave -noupdate -childformat {{/processor_tb/i_processor/RegsMIPS/regs(0) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(1) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(2) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(3) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(4) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(5) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(6) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(7) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(8) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(9) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(10) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(11) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(12) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(13) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(14) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(15) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(16) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(17) -radix decimal}} -expand -subitemconfig {/processor_tb/i_processor/RegsMIPS/regs(0) {-height 18 -radix decimal} /processor_tb/i_processor/RegsMIPS/regs(1) {-height 18 -radix decimal} /processor_tb/i_processor/RegsMIPS/regs(2) {-height 18 -radix decimal} /processor_tb/i_processor/RegsMIPS/regs(3) {-height 18 -radix decimal} /processor_tb/i_processor/RegsMIPS/regs(4) {-height 18 -radix decimal} /processor_tb/i_processor/RegsMIPS/regs(5) {-height 18 -radix decimal} /processor_tb/i_processor/RegsMIPS/regs(6) {-height 18 -radix decimal} /processor_tb/i_processor/RegsMIPS/regs(7) {-height 18 -radix decimal} /processor_tb/i_processor/RegsMIPS/regs(8) {-height 18 -radix decimal} /processor_tb/i_processor/RegsMIPS/regs(9) {-height 18 -radix decimal} /processor_tb/i_processor/RegsMIPS/regs(10) {-height 18 -radix decimal} /processor_tb/i_processor/RegsMIPS/regs(11) {-height 18 -radix decimal} /processor_tb/i_processor/RegsMIPS/regs(12) {-height 18 -radix decimal} /processor_tb/i_processor/RegsMIPS/regs(13) {-height 18 -radix decimal} /processor_tb/i_processor/RegsMIPS/regs(14) {-height 18 -radix decimal} /processor_tb/i_processor/RegsMIPS/regs(15) {-height 18 -radix decimal} /processor_tb/i_processor/RegsMIPS/regs(16) {-height 18 -radix decimal} /processor_tb/i_processor/RegsMIPS/regs(17) {-height 18 -radix decimal}} /processor_tb/i_processor/RegsMIPS/regs
add wave -noupdate -divider Dato_Inmediato
add wave -noupdate /processor_tb/i_processor/ID_Inm_ext
add wave -noupdate /processor_tb/i_processor/EX_Inm_ext
add wave -noupdate -divider data
add wave -noupdate -radix decimal /processor_tb/i_processor/WB_reg_RD_data
add wave -noupdate /processor_tb/i_processor/EX_AluControl
add wave -noupdate -radix decimal /processor_tb/i_processor/Alu_Op2
add wave -noupdate -radix decimal /processor_tb/i_processor/EX_Alu_Res
add wave -noupdate -radix decimal /processor_tb/i_processor/MEM_Alu_Res
add wave -noupdate -radix decimal /processor_tb/i_processor/WB_Alu_Res
add wave -noupdate /processor_tb/i_processor/WB_dataIn_Mem
add wave -noupdate /processor_tb/i_processor/MEM_dataIn_Mem
add wave -noupdate -divider Destino
add wave -noupdate /processor_tb/i_processor/ID_reg_RD
add wave -noupdate -radix decimal /processor_tb/i_processor/EX_reg_RD
add wave -noupdate /processor_tb/i_processor/EX_reg_Dest
add wave -noupdate -divider src
add wave -noupdate /processor_tb/i_processor/ID_reg_RS_number
add wave -noupdate /processor_tb/i_processor/EX_reg_RS_number
add wave -noupdate /processor_tb/i_processor/ID_reg_RS
add wave -noupdate -radix decimal /processor_tb/i_processor/EX_reg_RS
add wave -noupdate -divider temporal
add wave -noupdate /processor_tb/i_processor/ID_reg_RT_number
add wave -noupdate -radix decimal /processor_tb/i_processor/EX_reg_RT_number
add wave -noupdate /processor_tb/i_processor/ID_reg_RT
add wave -noupdate -radix decimal /processor_tb/i_processor/EX_reg_RT
add wave -noupdate /processor_tb/i_processor/MEM_reg_RT
add wave -noupdate -divider Forwarding
add wave -noupdate /processor_tb/i_processor/forwardA
add wave -noupdate /processor_tb/i_processor/forwardB
add wave -noupdate /processor_tb/i_processor/MEM_reg_RD_number
add wave -noupdate /processor_tb/i_processor/WB_reg_RD_number
TreeUpdate [SetDefaultTree]
WaveRestoreCursors {{Cursor 1} {1009 ns} 0}
quietly wave cursor active 1
configure wave -namecolwidth 332
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
WaveRestoreZoom {962 ns} {1168 ns}
